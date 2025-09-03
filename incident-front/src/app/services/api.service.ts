import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080'; 

  
  constructor(private http: HttpClient) { }

  get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.baseUrl}${endpoint}`, this.getHeaders());
  }

  post<T>(endpoint: string, data: any): Observable<T> {
    return this.http.post<T>(`${this.baseUrl}${endpoint}`, data, this.getHeaders());
  }

  put<T>(endpoint: string, data: any): Observable<T> {
    return this.http.put<T>(`${this.baseUrl}${endpoint}`, data, this.getHeaders());
  }

  delete<T>(endpoint: string): Observable<T> {
    const url = `${this.baseUrl}${endpoint}`;
    const headers = this.getHeaders();
    
    console.log('DELETE Request:', { url, headers });
    
    return this.http.delete<T>(url, headers);
  }

  patch<T>(endpoint: string, data: any): Observable<T> {
    return this.http.patch<T>(`${this.baseUrl}${endpoint}`, data, this.getHeaders());
  }

  private getHeaders(): { headers: HttpHeaders } {
    const token = localStorage.getItem('token');
    
    let headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    if (token) {
      // Verifica se o token está expirado
      try {
        const payload = JSON.parse(atob(token.split('.')[1]));
        const expirationTime = payload.exp * 1000; // Converte para milissegundos
        const currentTime = Date.now();
        
        if (currentTime >= expirationTime) {
          console.log('Token expirado, removendo...');
          localStorage.removeItem('token');
          localStorage.removeItem('userName');
          return { headers };
        }
        
        headers = headers.set('Authorization', `Bearer ${token}`);
        console.log('Token encontrado:', token.substring(0, 20) + '...');
      } catch (error) {
        console.error('Erro ao decodificar token:', error);
        localStorage.removeItem('token');
        localStorage.removeItem('userName');
      }
    } else {
      console.warn('Nenhum token encontrado no localStorage');
    }

    return { headers };
  }
}
