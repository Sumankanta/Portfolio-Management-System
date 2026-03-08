import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, of, throwError } from 'rxjs';
import { delay, tap } from 'rxjs/operators';
import { User, AuthResponse } from '../models';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly TOKEN_KEY = 'portfolio_token';
  private readonly USER_KEY  = 'portfolio_user';

  private currentUserSubject = new BehaviorSubject<User | null>(
    this.getStoredUser()
  );
  public currentUser$ = this.currentUserSubject.asObservable();

  private mockUsers: (User & { password: string })[] = [
    { id: 1, fullName: 'Admin User',  email: 'admin@example.com',  password: 'Admin@123',  role: 'ADMIN',  createdAt: new Date() },
    { id: 2, fullName: 'Viewer User', email: 'viewer@example.com', password: 'Viewer@123', role: 'VIEWER', createdAt: new Date() }
  ];

  constructor(private router: Router) {}

  login(email: string, password: string): Observable<AuthResponse> {
    const found = this.mockUsers.find(
      u => u.email === email && u.password === password
    );
    if (!found) {
      return throwError(() => new Error('Invalid email or password. Try admin@example.com / Admin@123'));
    }
    const { password: _, ...safeUser } = found;
    const token = `mock_jwt_${safeUser.id}_${Date.now()}`;
    return of({ token, user: safeUser }).pipe(
      delay(800),
      tap(res => {
        localStorage.setItem(this.TOKEN_KEY, res.token);
        localStorage.setItem(this.USER_KEY, JSON.stringify(res.user));
        this.currentUserSubject.next(res.user);
      })
    );
  }

  register(fullName: string, email: string, password: string, role: 'ADMIN' | 'VIEWER'): Observable<AuthResponse> {
    if (this.mockUsers.find(u => u.email === email)) {
      return throwError(() => new Error('Email already registered'));
    }
    const newUser = { id: this.mockUsers.length + 1, fullName, email, password, role, createdAt: new Date() };
    this.mockUsers.push(newUser);
    const { password: _, ...safeUser } = newUser;
    const token = `mock_jwt_${safeUser.id}_${Date.now()}`;
    return of({ token, user: safeUser }).pipe(
      delay(800),
      tap(res => {
        localStorage.setItem(this.TOKEN_KEY, res.token);
        localStorage.setItem(this.USER_KEY, JSON.stringify(res.user));
        this.currentUserSubject.next(res.user);
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.currentUserSubject.next(null);
    this.router.navigate(['/auth/login']);
  }

  isAuthenticated(): boolean { return !!localStorage.getItem(this.TOKEN_KEY); }
  getToken(): string | null  { return localStorage.getItem(this.TOKEN_KEY); }
  getCurrentUser(): User | null { return this.currentUserSubject.value; }

  private getStoredUser(): User | null {
    const s = localStorage.getItem(this.USER_KEY);
    return s ? JSON.parse(s) : null;
  }
}
