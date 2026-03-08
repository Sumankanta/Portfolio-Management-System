import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth';

function passwordMatch(control: AbstractControl): ValidationErrors | null {
  const pwd = control.get('password');
  const cfm = control.get('confirmPassword');
  if (pwd && cfm && pwd.value !== cfm.value) {
    cfm.setErrors({ mismatch: true });
    return { mismatch: true };
  }
  return null;
}

function strongPassword(control: AbstractControl): ValidationErrors | null {
  const v = control.value || '';
  if (!v) return null;
  if (!/[A-Z]/.test(v) || !/[0-9]/.test(v)) return { weakPassword: true };
  return null;
}

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class RegisterComponent {
  private fb          = inject(FormBuilder);
  private authService = inject(AuthService);
  private router      = inject(Router);

  form = this.fb.group({
    fullName:        ['', [Validators.required, Validators.minLength(3)]],
    email:           ['', [Validators.required, Validators.email]],
    role:            ['', [Validators.required]],
    password:        ['', [Validators.required, Validators.minLength(8), strongPassword]],
    confirmPassword: ['', [Validators.required]]
  }, { validators: passwordMatch });

  loading       = false;
  errorMsg      = '';
  showPwd       = false;
  showConfirm   = false;
  strengthLevel = 0;
  strengthLabel = '';
  strengthColor = '';

  get f() { return this.form.controls; }

  isInvalid(field: string): boolean {
    const c = this.form.get(field);
    return !!(c?.invalid && c?.touched);
  }

  onPasswordInput(): void {
    const v = this.f['password'].value || '';
    let s = 0;
    if (v.length >= 8)          s++;
    if (/[A-Z]/.test(v))        s++;
    if (/[0-9]/.test(v))        s++;
    if (/[^A-Za-z0-9]/.test(v)) s++;
    this.strengthLevel = s;
    const labels = ['', 'Weak', 'Fair', 'Good', 'Strong'];
    const colors  = ['', 'danger', 'warning', 'info', 'success'];
    this.strengthLabel = labels[s];
    this.strengthColor = colors[s];
  }

  onSubmit(): void {
    if (this.form.invalid) { this.form.markAllAsTouched(); return; }
    this.loading  = true;
    this.errorMsg = '';
    const { fullName, email, password, role } = this.form.value;
    this.authService.register(fullName!, email!, password!, role as 'ADMIN' | 'VIEWER').subscribe({
      next:  ()    => this.router.navigate(['/admin/dashboard']),
      error: (err) => { this.errorMsg = err.message; this.loading = false; }
    });
  }
}
