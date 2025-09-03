import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Incident } from '../../interfaces/incident.interface';

@Component({
  selector: 'app-delete-incident-modal',
  imports: [CommonModule],
  templateUrl: './delete-incident-modal.html',
  styleUrl: './delete-incident-modal.scss'
})
export class DeleteIncidentModal {
  @Input() showModal = false;
  @Input() incident: Incident | null = null;
  @Output() modalClosed = new EventEmitter<void>();
  @Output() incidentDeleted = new EventEmitter<string>();

  isLoading = false;

  closeModal(): void {
    this.modalClosed.emit();
  }

  confirmDelete(): void {
    if (!this.incident) return;

    this.isLoading = true;
    
    // Emite o evento com o ID do incidente para ser deletado
    this.incidentDeleted.emit(this.incident.id);
  }

  getCommentsCount(): number {
    return this.incident?.comments?.length || 0;
  }
}
