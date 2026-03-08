import { Routes } from '@angular/router';
import { authGuard }  from './core/guards/auth-guard';
import { guestGuard } from './core/guards/guest-guard';

export const routes: Routes = [
  { path: '', redirectTo: '/auth/login', pathMatch: 'full' },
  {
    path: 'auth',
    loadComponent: () => import('./layouts/auth-layout/auth-layout').then(m => m.AuthLayout),
    canActivate: [guestGuard],
    children: [
      { path: 'login',    loadComponent: () => import('./modules/auth/login/login').then(m => m.LoginComponent) },
      { path: 'register', loadComponent: () => import('./modules/auth/register/register').then(m => m.RegisterComponent) },
      { path: '', redirectTo: 'login', pathMatch: 'full' }
    ]
  },
  {
    path: 'admin',
    loadComponent: () => import('./layouts/admin-layout/admin-layout').then(m => m.AdminLayoutComponent),
    canActivate: [authGuard],
    children: [
      { path: 'dashboard',     loadComponent: () => import('./modules/dashboard/dashboard').then(m => m.Dashboard) },
      { path: 'skills',        loadComponent: () => import('./modules/skills/skills').then(m => m.Skills) },
      { path: 'employment',    loadComponent: () => import('./modules/employment/employment').then(m => m.Employment) },
      { path: 'projects',      loadComponent: () => import('./modules/projects/projects').then(m => m.Projects) },
      { path: 'clients',       loadComponent: () => import('./modules/clients/clients').then(m => m.Clients) },
      { path: 'chat',          loadComponent: () => import('./modules/chat/chat').then(m => m.Chat) },
      { path: 'analytics',     loadComponent: () => import('./modules/analytics/analytics').then(m => m.Analytics) },
      { path: 'ui-management', loadComponent: () => import('./modules/ui-management/ui-management').then(m => m.UiManagement) },
      { path: 'system-logs',   loadComponent: () => import('./modules/system-logs/system-logs').then(m => m.SystemLogs) },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: '/auth/login' }
];
