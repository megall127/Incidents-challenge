import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';

export const AuthInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.log('Interceptor capturou erro:', error.status, error.url, error.error);
      
      if (error.status === 401) {
        // Verifica se é um erro real de autenticação
        if (error.error && typeof error.error === 'object') {
          // Se tem dados no erro, pode ser uma resposta de sucesso com status incorreto
          console.log('Erro 401 com dados:', error.error);
          // Deixa o componente tratar o erro
          return throwError(() => error);
        }
        
        // Token expirado ou inválido real (sem dados de erro)
        console.log('Token JWT expirado ou inválido. Redirecionando para login...');
        
        // Limpa o localStorage
        localStorage.removeItem('token');
        localStorage.removeItem('userName');

        router.navigate(['/login']);
      }
      
      return throwError(() => error);
    })
  );
};
