import { Component, Input, Output, EventEmitter, OnInit, OnDestroy, HostListener, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService }  from '../../../core/services/auth';
import { ThemeService } from '../../../core/services/theme';
import { User, Notification } from '../../../core/models';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class NavbarComponent implements OnInit, OnDestroy {
  @Input()  sidebarCollapsed = false;
  @Output() toggleSidebar    = new EventEmitter<void>();

  private authService  = inject(AuthService);
  private themeService = inject(ThemeService);
  private sub!: Subscription;

  currentUser: User | null = null;
  searchQuery  = '';
  showNotifs   = false;
  showUserMenu = false;
  isDark       = false;

  notifications: Notification[] = [
    { id: 1, icon: 'bi-check-circle-fill',        iconClass: 'green',  title: 'Project "Portfolio Platform" updated',        time: '2 min ago',   read: false },
    { id: 2, icon: 'bi-exclamation-triangle-fill', iconClass: 'orange', title: 'High API latency on /api/projects',           time: '15 min ago',  read: false },
    { id: 3, icon: 'bi-person-fill-add',           iconClass: 'blue',   title: 'New visitor session started',                 time: '1 hour ago',  read: false },
    { id: 4, icon: 'bi-database-fill',             iconClass: 'red',    title: 'Database backup completed successfully',      time: '3 hours ago', read: true  },
    { id: 5, icon: 'bi-bar-chart-fill',            iconClass: 'blue',   title: 'Monthly analytics report is ready',           time: '1 day ago',   read: true  }
  ];

  get unreadCount(): number { return this.notifications.filter(n => !n.read).length; }

  get userInitials(): string {
    return this.currentUser?.fullName.split(' ').map(w => w[0]).join('').toUpperCase().slice(0, 2) ?? 'AU';
  }

  ngOnInit(): void {
    this.currentUser = this.authService.getCurrentUser();
    this.sub = this.themeService.isDark$.subscribe(d => this.isDark = d);
  }

  ngOnDestroy(): void { this.sub?.unsubscribe(); }

  toggleTheme(): void { this.themeService.toggle(); }

  openNotifs(e: Event): void {
    e.stopPropagation();
    this.showNotifs   = !this.showNotifs;
    this.showUserMenu = false;
  }

  openUserMenu(e: Event): void {
    e.stopPropagation();
    this.showUserMenu = !this.showUserMenu;
    this.showNotifs   = false;
  }

  markRead(n: Notification): void { n.read = true; }
  markAllRead(): void              { this.notifications.forEach(n => n.read = true); }
  logout(): void                   { this.authService.logout(); }

  @HostListener('document:click')
  onOutside(): void {
    this.showNotifs   = false;
    this.showUserMenu = false;
  }
}
