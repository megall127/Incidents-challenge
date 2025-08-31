import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { LoginRequest, LoginResponse, RegisterRequest, RegisterResponse } from '../interfaces/auth.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  constructor(private apiService: ApiService) { }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    console.log('Login request:', credentials);
    return this.apiService.post<LoginResponse>('/auth/login', credentials);
  }

  register(userData: RegisterRequest): Observable<RegisterResponse> {
    return this.apiService.post<RegisterResponse>('/auth/register', userData);
  }

  isLoggedIn(): boolean {
    const token = localStorage.getItem('token');
    return !!token;
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  setToken(token: string): void {
    localStorage.setItem('token', token);
  }
}
