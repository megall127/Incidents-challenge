import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IncidentRequest } from '../../interfaces/incident.interface';

@Component({
  selector: 'app-create-incident-modal',
  imports: [CommonModule, FormsModule],
  templateUrl: './create-incident-modal.html',
  styleUrl: './create-incident-modal.scss'
})
export class CreateIncidentModal {
  @Input() showModal = false;
  @Output() modalClosed = new EventEmitter<void>();
  @Output() incidentCreated = new EventEmitter<IncidentRequest>();

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

  createIncident(): void {
    if (!this.incidentData.titulo || !this.incidentData.responsavelEmail) {
      this.errorMessage = 'Título e email do responsável são obrigatórios';
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.incidentCreated.emit(this.incidentData);
    
    this.successMessage = 'Incidente criado com sucesso!';
    this.isLoading = false;
    
    setTimeout(() => {
      this.closeModal();
    }, 1500);
  }
}
