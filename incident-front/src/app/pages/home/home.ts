import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth';
import { IncidentService } from '../../services/incident.service';
import { Incident, IncidentRequest, Comment, CommentRequest } from '../../interfaces/incident.interface';

@Component({
  selector: 'app-home',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home implements OnInit {
  showModal = false;
  isLoading = false;
  errorMessage = '';
  successMessage = '';
  incidents: Incident[] = [];
  loadingIncidents = false;

  comments: { [incidentId: string]: Comment[] } = {};
  newComment: { [incidentId: string]: string } = {};
  loadingComments: { [incidentId: string]: boolean } = {};
  submittingComment: { [incidentId: string]: boolean } = {};

  incidentData: IncidentRequest = {
    titulo: '',
    descricao: '',
    prioridade: 'MEDIA',
    status: 'ABERTO',
    responsavelEmail: '',
    tags: []
  };

  prioridades = ['BAIXA', 'MEDIA', 'ALTA', 'CRITICA'];
  status = ['ABERTO', 'EM_ANDAMENTO', 'RESOLVIDA', 'CANCELADA'];

  constructor(
    private authService: AuthService,
    private router: Router,
    private incidentService: IncidentService
  ) { }

  ngOnInit(): void {
    this.loadIncidents();
  }

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
      status: 'ABERTO',
      responsavelEmail: '',
      tags: []
    };
    this.errorMessage = '';
    this.successMessage = '';
  }

  loadIncidents(): void {
    this.loadingIncidents = true;
    this.incidentService.getAllIncidents().subscribe({
      next: (incidents) => {
        this.incidents = incidents;
        this.loadingIncidents = false;
        incidents.forEach(incident => {
          this.loadComments(incident.id);
        });
      },
      error: (error) => {
        console.error('Erro ao carregar incidentes:', error);
        this.loadingIncidents = false;
      }
    });
  }

  loadComments(incidentId: string): void {
    this.loadingComments[incidentId] = true;
    this.incidentService.getCommentsByIncidentId(incidentId).subscribe({
      next: (comments) => {
        this.comments[incidentId] = comments;
        this.loadingComments[incidentId] = false;
      },
      error: (error) => {
        console.error('Erro ao carregar comentários:', error);
        this.comments[incidentId] = [];
        this.loadingComments[incidentId] = false;
      }
    });
  }

  addComment(incidentId: string): void {
    const commentText = this.newComment[incidentId]?.trim();
    if (!commentText) return;

    this.submittingComment[incidentId] = true;
    
    const commentRequest: CommentRequest = {
      autor: this.authService.getUserName() || 'Usuário',
      mensagem: commentText
    };

    this.incidentService.createComment(incidentId, commentRequest).subscribe({
      next: (comment) => {
        if (!this.comments[incidentId]) {
          this.comments[incidentId] = [];
        }
        this.comments[incidentId].push(comment);
        this.newComment[incidentId] = '';
        this.submittingComment[incidentId] = false;
      },
      error: (error) => {
        console.error('Erro ao adicionar comentário:', error);
        this.submittingComment[incidentId] = false;
      }
    });
  }

  deleteComment(commentId: string, incidentId: string): void {
    this.incidentService.deleteComment(commentId).subscribe({
      next: () => {
        this.comments[incidentId] = this.comments[incidentId].filter(c => c.id !== commentId);
      },
      error: (error) => {
        console.error('Erro ao deletar comentário:', error);
      }
    });
  }

  addTag(): void {
    const tag = prompt('Digite uma tag:');
    if (tag && tag.trim()) {
      if (!this.incidentData.tags) {
        this.incidentData.tags = [];
      }
      this.incidentData.tags.push(tag.trim());
    }
  }

  removeTag(index: number): void {
    if (this.incidentData.tags) {
      this.incidentData.tags.splice(index, 1);
    }
  }

  createIncident(): void {
    if (!this.incidentData.titulo || !this.incidentData.responsavelEmail) {
      this.errorMessage = 'Título e email do responsável são obrigatórios';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.incidentService.createIncident(this.incidentData).subscribe({
      next: (incident) => {
        this.isLoading = false;
        this.successMessage = 'Incidente criado com sucesso!';
        this.incidents.unshift(incident);
        
        setTimeout(() => {
          this.closeModal();
        }, 1500);
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMessage = 'Erro ao criar incidente. Tente novamente.';
        console.error('Erro ao criar incidente:', error);
      }
    });
  }

  getPriorityClass(prioridade: string): string {
    switch (prioridade) {
      case 'CRITICA': return 'priority-critical';
      case 'ALTA': return 'priority-high';
      case 'MEDIA': return 'priority-medium';
      case 'BAIXA': return 'priority-low';
      default: return 'priority-medium';
    }
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'ABERTO': return 'status-open';
      case 'EM_ANDAMENTO': return 'status-in-progress';
      case 'RESOLVIDA': return 'status-resolved';
      case 'CANCELADA': return 'status-cancelled';
      default: return 'status-open';
    }
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
