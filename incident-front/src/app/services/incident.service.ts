import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';
import { Incident, IncidentRequest, Comment, CommentRequest } from '../interfaces/incident.interface';

@Injectable({
  providedIn: 'root'
})
export class IncidentService {
  constructor(private apiService: ApiService) { }

  getAllIncidents(): Observable<Incident[]> {
    return this.apiService.get<Incident[]>('/incidents');
  }

  getIncidentById(id: string): Observable<Incident> {
    return this.apiService.get<Incident>(`/incidents/${id}`);
  }

  createIncident(incident: IncidentRequest): Observable<Incident> {
    return this.apiService.post<Incident>('/incidents', incident);
  }

  updateIncident(id: string, incident: Partial<IncidentRequest>): Observable<Incident> {
    return this.apiService.put<Incident>(`/incidents/${id}`, incident);
  }

  deleteIncident(id: string): Observable<any> {
    return this.apiService.delete<any>(`/incidents/${id}`);
  }

  updateIncidentStatus(id: string, status: string): Observable<any> {
    return this.apiService.patch<any>(`/incidents/${id}/status`, { status });
  }


  // Métodos para comentários
  getCommentsByIncidentId(incidentId: string): Observable<Comment[]> {
    return this.apiService.get<Comment[]>(`/incidents/${incidentId}/comments`);
  }

  createComment(incidentId: string, comment: CommentRequest): Observable<Comment> {
    return this.apiService.post<Comment>(`/incidents/${incidentId}/comments`, comment);
  }

  deleteComment(commentId: string): Observable<any> {
    return this.apiService.delete<any>(`/comments/${commentId}`);
  }
}
