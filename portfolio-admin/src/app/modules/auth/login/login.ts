import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {
  private fb          = inject(FormBuilder);
  private authService = inject(AuthService);
  private router      = inject(Router);

  form = this.fb.group({
    email:    ['admin@example.com', [Validators.required, Validators.email]],
    password: ['Admin@123',         [Validators.required, Validators.minLength(8)]]
  });

  loading      = false;
  errorMsg     = '';
  showPassword = false;

  get f() { return this.form.controls; }

  isInvalid(field: string): boolean {
    const c = this.form.get(field);
    return !!(c?.invalid && c?.touched);
  }

  onSubmit(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.loading  = true;
    this.errorMsg = '';
    const { email, password } = this.form.value;
    this.authService.login(email!, password!).subscribe({
      next:  ()    => this.router.navigate(['/admin/dashboard']),
      error: (err) => { this.errorMsg = err.message; this.loading = false; }
    });
  }
}
