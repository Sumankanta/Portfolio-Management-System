import { Component, OnInit, OnDestroy, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NavbarComponent }  from '../../shared/components/navbar/navbar';
import { SidebarComponent } from '../../shared/components/sidebar/sidebar';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [RouterOutlet, CommonModule, NavbarComponent, SidebarComponent],
  templateUrl: './admin-layout.html',
  styleUrl: './admin-layout.css'
})
export class AdminLayoutComponent implements OnInit, OnDestroy {
  sidebarCollapsed = signal(false);
  isMobile         = signal(false);

  private resizeFn = () => this.checkMobile();

  ngOnInit(): void {
    this.checkMobile();
    window.addEventListener('resize', this.resizeFn);
  }

  ngOnDestroy(): void {
    window.removeEventListener('resize', this.resizeFn);
  }

  toggle(): void {
    this.sidebarCollapsed.update(v => !v);
  }

  private checkMobile(): void {
    const mobile = window.innerWidth < 992;
    this.isMobile.set(mobile);
    if (mobile) this.sidebarCollapsed.set(true);
    else this.sidebarCollapsed.set(false);
  }
}
