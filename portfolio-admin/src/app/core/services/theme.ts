import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private readonly KEY = 'portfolio_theme';
  private darkSubject  = new BehaviorSubject<boolean>(
    localStorage.getItem(this.KEY) === 'dark'
  );
  public isDark$ = this.darkSubject.asObservable();

  constructor() { this.apply(this.darkSubject.value); }

  toggle(): void {
    const d = !this.darkSubject.value;
    this.darkSubject.next(d);
    this.apply(d);
    localStorage.setItem(this.KEY, d ? 'dark' : 'light');
  }

  get isDark(): boolean { return this.darkSubject.value; }

  private apply(dark: boolean): void {
    document.documentElement.setAttribute('data-theme', dark ? 'dark' : 'light');
  }
}
