import { Component, Input, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../../core/services/auth';

interface NavItem {
  icon:   string;
  label:  string;
  route:  string;
  badge?: number;
  color?: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.css'
})
export class SidebarComponent {
  @Input() collapsed = false;
  private auth = inject(AuthService);

  mainItems: NavItem[] = [
    { icon: 'bi-speedometer2',     label: 'Dashboard',  route: '/admin/dashboard',  color: '#4F6EF7' },
    { icon: 'bi-lightning-charge', label: 'Skills',     route: '/admin/skills',     color: '#F59E0B' },
    { icon: 'bi-briefcase',        label: 'Employment', route: '/admin/employment', color: '#22C55E' },
    { icon: 'bi-folder2-open',     label: 'Projects',   route: '/admin/projects',   color: '#8B5CF6' },
    { icon: 'bi-people',           label: 'Clients',    route: '/admin/clients',    color: '#06B6D4' },
    { icon: 'bi-chat-dots',        label: 'Chat',       route: '/admin/chat',       color: '#EC4899', badge: 3 }
  ];

  systemItems: NavItem[] = [
    { icon: 'bi-graph-up-arrow', label: 'Analytics',     route: '/admin/analytics',     color: '#4F6EF7' },
    { icon: 'bi-sliders',        label: 'UI Management', route: '/admin/ui-management', color: '#6B7280' },
    { icon: 'bi-journal-text',   label: 'System Logs',   route: '/admin/system-logs',   color: '#EF4444' }
  ];

  logout(): void { this.auth.logout(); }
}
