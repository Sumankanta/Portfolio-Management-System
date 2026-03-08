export interface User {
  id: number;
  fullName: string;
  email: string;
  role: 'ADMIN' | 'VIEWER';
  avatarUrl?: string;
  createdAt: Date;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface Notification {
  id: number;
  icon: string;
  iconClass: string;
  title: string;
  time: string;
  read: boolean;
}
