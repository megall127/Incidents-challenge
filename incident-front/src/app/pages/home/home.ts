import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth';

@Component({
  selector: 'app-home',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {
  showModal = false;
  isLoading = false;
  errorMessage = '';
  successMessage = '';

  incidentData = {
    titulo: '',
    descricao: '',
    prioridade: 'MEDIA',
    status: 'ABERTA',
    responsavelEmail: '',
    tags: [] as string[]
  };

  prioridades = ['BAIXA', 'MEDIA', 'ALTA', 'CRITICA'];
  status = ['ABERTA', 'EM_ANDAMENTO', 'RESOLVIDA', 'CANCELADA'];

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  openModal(): void {
    this.showModal = true;
    this.resetForm();
  }

  closeModal(): void {
    this.showModal = false;
    this.resetForm();
  }

  resetForm(): void {
    this.incidentData = {
      titulo: '',
      descricao: '',
      prioridade: 'MEDIA',
      status: 'ABERTA',
      responsavelEmail: '',
      tags: []
    };
    this.errorMessage = '';
    this.successMessage = '';
  }

  addTag(): void {
    const tag = prompt('Digite uma tag:');
    if (tag && tag.trim()) {
      this.incidentData.tags.push(tag.trim());
    }
  }

  removeTag(index: number): void {
    this.incidentData.tags.splice(index, 1);
  }

  createIncident(): void {
    if (!this.incidentData.titulo || !this.incidentData.responsavelEmail) {
      this.errorMessage = 'Título e email do responsável são obrigatórios';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    // Aqui você vai implementar a chamada para o service de incidentes
    console.log('Dados do incidente:', this.incidentData);
    
    // Simulando uma requisição
    setTimeout(() => {
      this.isLoading = false;
      this.successMessage = 'Incidente criado com sucesso!';
      
      setTimeout(() => {
        this.closeModal();
      }, 1500);
    }, 1000);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
