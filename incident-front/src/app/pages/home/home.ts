import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth';
import { IncidentService } from '../../services/incident.service';
import { Incident, IncidentRequest, Comment, CommentRequest } from '../../interfaces/incident.interface';
import { CreateIncidentModal } from '../../components/create-incident-modal';

@Component({
  selector: 'app-home',
  imports: [CommonModule, FormsModule, RouterModule, CreateIncidentModal],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home implements OnInit {
  showModal = false;
  incidents: Incident[] = [];
  loadingIncidents = false;

  comments: { [incidentId: string]: Comment[] } = {};
  newComment: { [incidentId: string]: string } = {};
  loadingComments: { [incidentId: string]: boolean } = {};
  submittingComment: { [incidentId: string]: boolean } = {};

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
  }

  closeModal(): void {
    this.showModal = false;
  }

  loadIncidents(): void {
    this.loadingIncidents = true;
    this.incidentService.getAllIncidents().subscribe({
      next: (incidents) => {
        this.incidents = incidents;
        this.loadingIncidents = false;
        incidents.forEach(incident => {
          console.log(incident)
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

  onIncidentCreated(incidentData: IncidentRequest): void {
    this.incidentService.createIncident(incidentData).subscribe({
      next: (incident) => {
        this.incidents.unshift(incident);
        this.closeModal();
      },
      error: (error) => {
        console.error('Erro ao criar incidente:', error);
        alert('Erro ao criar incidente. Tente novamente.');
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

  editIncident(incident: Incident): void {
    alert(`Editar incidente: ${incident.titulo}`);
    console.log('Editar incidente:', incident);
  }

  deleteIncident(incidentId: string): void {
    if (confirm('Tem certeza que deseja excluir este incidente? Esta ação não pode ser desfeita.')) {
      this.incidentService.deleteIncident(incidentId).subscribe({
        next: () => {
          this.incidents = this.incidents.filter(incident => incident.id !== incidentId);
          delete this.comments[incidentId];
          delete this.newComment[incidentId];
          delete this.loadingComments[incidentId];
          delete this.submittingComment[incidentId];
          
          alert('Incidente excluído com sucesso!');
        },
        error: (error) => {
          console.error('Erro ao excluir incidente:', error);
          alert('Erro ao excluir incidente. Tente novamente.');
        }
      });
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
