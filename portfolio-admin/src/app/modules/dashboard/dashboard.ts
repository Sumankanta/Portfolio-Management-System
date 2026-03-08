import {
  Component, OnInit, AfterViewInit,
  ViewChild, ElementRef
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart, registerables } from 'chart.js';

Chart.register(...registerables);

interface StatCard {
  title:    string;
  value:    string;
  change:   string;
  positive: boolean;
  icon:     string;
  color:    string;
  bg:       string;
}

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit, AfterViewInit {

  @ViewChild('lineChart')   lineChartRef!:   ElementRef<HTMLCanvasElement>;
  @ViewChild('donutChart')  donutChartRef!:  ElementRef<HTMLCanvasElement>;
  @ViewChild('barChart')    barChartRef!:    ElementRef<HTMLCanvasElement>;

  greeting = '';
  currentDate = new Date().toLocaleDateString('en-US', {
    weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'
  });

  stats: StatCard[] = [
    { title: 'Total Projects',   value: '24',    change: '+3 this month',  positive: true,  icon: 'bi-folder2-open',     color: '#4F6EF7', bg: 'rgba(79,110,247,0.12)'  },
    { title: 'Active Clients',   value: '12',    change: '+2 this month',  positive: true,  icon: 'bi-people-fill',      color: '#22C55E', bg: 'rgba(34,197,94,0.12)'   },
    { title: 'Skills Listed',    value: '38',    change: '+5 this month',  positive: true,  icon: 'bi-lightning-charge', color: '#F59E0B', bg: 'rgba(245,158,11,0.12)'  },
    { title: 'API Requests',     value: '9.4K',  change: '-8% this week',  positive: false, icon: 'bi-graph-up-arrow',   color: '#8B5CF6', bg: 'rgba(139,92,246,0.12)'  },
    { title: 'Profile Views',    value: '1,284', change: '+22% this week', positive: true,  icon: 'bi-eye',              color: '#06B6D4', bg: 'rgba(6,182,212,0.12)'   },
    { title: 'Chat Messages',    value: '47',    change: '+12 today',      positive: true,  icon: 'bi-chat-dots-fill',   color: '#EC4899', bg: 'rgba(236,72,153,0.12)'  }
  ];

  recentProjects = [
    { name: 'Portfolio Platform', client: 'Personal', status: 'Active',      statusClass: 'success', tech: ['Angular', 'Spring Boot', 'MySQL'],    date: 'Mar 2026' },
    { name: 'Task Manager App',   client: 'Internal', status: 'Completed',   statusClass: 'primary', tech: ['Angular', 'Node.js', 'MongoDB'],      date: 'Feb 2026' },
    { name: 'E-Commerce Dashboard', client: 'Acme Corp', status: 'In Progress', statusClass: 'warning', tech: ['React', 'Python', 'PostgreSQL'],   date: 'Jan 2026' },
    { name: 'Real Estate Portal', client: 'PropCo',   status: 'Completed',   statusClass: 'primary', tech: ['Vue.js', 'Laravel', 'MySQL'],        date: 'Dec 2025' },
    { name: 'HR Management',      client: 'TechFirm', status: 'Active',      statusClass: 'success', tech: ['Angular', 'Spring Boot', 'Oracle'],  date: 'Nov 2025' },
  ];

  topSkills = [
    { name: 'Angular',     level: 92, color: '#4F6EF7' },
    { name: 'Spring Boot', level: 88, color: '#22C55E' },
    { name: 'TypeScript',  level: 85, color: '#F59E0B' },
    { name: 'MySQL',       level: 80, color: '#8B5CF6' },
    { name: 'Docker',      level: 72, color: '#06B6D4' },
    { name: 'AWS',         level: 65, color: '#EC4899' },
  ];

  ngOnInit(): void {
    const h = new Date().getHours();
    this.greeting = h < 12 ? 'Good Morning' : h < 17 ? 'Good Afternoon' : 'Good Evening';
  }

  ngAfterViewInit(): void {
    this.buildLineChart();
    this.buildDonutChart();
    this.buildBarChart();
  }

  private buildLineChart(): void {
    const months = ['Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'Mar'];
    new Chart(this.lineChartRef.nativeElement, {
      type: 'line',
      data: {
        labels: months,
        datasets: [
          {
            label: 'API Requests (K)',
            data: [5.2, 6.8, 7.1, 8.4, 7.9, 9.1, 9.4],
            borderColor: '#4F6EF7',
            backgroundColor: 'rgba(79,110,247,0.08)',
            borderWidth: 2.5,
            tension: 0.4,
            fill: true,
            pointBackgroundColor: '#4F6EF7',
            pointRadius: 4,
            pointHoverRadius: 6
          },
          {
            label: 'Profile Views (K)',
            data: [0.8, 1.1, 0.9, 1.3, 1.0, 1.2, 1.28],
            borderColor: '#22C55E',
            backgroundColor: 'rgba(34,197,94,0.06)',
            borderWidth: 2.5,
            tension: 0.4,
            fill: true,
            pointBackgroundColor: '#22C55E',
            pointRadius: 4,
            pointHoverRadius: 6
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'top',
            align: 'end',
            labels: { boxWidth: 10, boxHeight: 10, borderRadius: 3, usePointStyle: true, font: { size: 12 } }
          }
        },
        scales: {
          x: { grid: { display: false }, ticks: { font: { size: 11 } } },
          y: { grid: { color: 'rgba(0,0,0,0.04)' }, ticks: { font: { size: 11 } }, beginAtZero: false }
        }
      }
    });
  }

  private buildDonutChart(): void {
    new Chart(this.donutChartRef.nativeElement, {
      type: 'doughnut',
      data: {
        labels: ['Active', 'Completed', 'In Progress'],
        datasets: [{
          data: [8, 12, 4],
          backgroundColor: ['#4F6EF7', '#22C55E', '#F59E0B'],
          borderWidth: 0,
          hoverOffset: 8
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        cutout: '72%',
        plugins: {
          legend: {
            position: 'bottom',
            labels: { boxWidth: 10, boxHeight: 10, borderRadius: 3, usePointStyle: true, font: { size: 12 }, padding: 16 }
          }
        }
      }
    });
  }

  private buildBarChart(): void {
    new Chart(this.barChartRef.nativeElement, {
      type: 'bar',
      data: {
        labels: ['Sep', 'Oct', 'Nov', 'Dec', 'Jan', 'Feb', 'Mar'],
        datasets: [{
          label: 'Projects Completed',
          data: [1, 2, 1, 3, 2, 1, 2],
          backgroundColor: 'rgba(79,110,247,0.85)',
          borderRadius: 6,
          borderSkipped: false
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: {
          x: { grid: { display: false }, ticks: { font: { size: 11 } } },
          y: { grid: { color: 'rgba(0,0,0,0.04)' }, ticks: { stepSize: 1, font: { size: 11 } }, beginAtZero: true }
        }
      }
    });
  }
}
