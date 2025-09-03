import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Incident, IncidentRequest } from '../../interfaces/incident.interface';

@Component({
  selector: 'app-edit-incident-modal',
  imports: [CommonModule, FormsModule],
  templateUrl: './edit-incident-modal.html',
  styleUrl: './edit-incident-modal.scss'
})
export class EditIncidentModal implements OnInit {
  @Input() showModal = false;
  @Input() incident: Incident | null = null;
  @Output() modalClosed = new EventEmitter<void>();
  @Output() incidentUpdated = new EventEmitter<{ id: string, data: IncidentRequest }>();

  isLoading = false;
  errorMessage = '';
  successMessage = '';

  incidentData: IncidentRequest = {
    titulo: '',
    descricao: '',
    prioridade: 'MEDIA',
    status: 'ABERTO',
    responsavelEmail: '',
    tags: []
  };

  prioridades = ['BAIXA', 'MEDIA', 'ALTA'];
  status = ['ABERTO', 'EM_ANDAMENTO', 'RESOLVIDA', 'CANCELADA'];

  ngOnInit(): void {
    if (this.incident) {
      this.loadIncidentData();
    }
  }

  ngOnChanges(): void {
    if (this.incident && this.showModal) {
      this.loadIncidentData();
    }
  }

  loadIncidentData(): void {
    if (this.incident) {
      this.incidentData = {
        titulo: this.incident.titulo || '',
        descricao: this.incident.descricao || '',
        prioridade: this.incident.prioridade || 'MEDIA',
        status: this.incident.status || 'ABERTO',
        responsavelEmail: this.incident.responsavelEmail || '',
        tags: this.incident.tags ? [...this.incident.tags] : []
      };
    }
  }

  closeModal(): void {
    this.modalClosed.emit();
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

  updateIncident(): void {
    if (!this.incidentData.titulo || !this.incidentData.responsavelEmail) {
      this.errorMessage = 'Título e email do responsável são obrigatórios';
      return;
    }

    if (!this.incident) {
      this.errorMessage = 'Incidente não encontrado';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.incidentUpdated.emit({
      id: this.incident.id,
      data: this.incidentData
    });
    
    this.successMessage = 'Incidente atualizado com sucesso!';
    this.isLoading = false;
    
    setTimeout(() => {
      this.closeModal();
    }, 1500);
  }
}
