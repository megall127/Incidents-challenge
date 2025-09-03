export interface Incident {
  id: string;
  titulo: string;
  descricao?: string;
  prioridade: 'BAIXA' | 'MEDIA' | 'ALTA';
  status: 'ABERTO' | 'EM_ANDAMENTO' | 'RESOLVIDA' | 'CANCELADA';
  responsavelEmail: string;
  tags?: string[];
  dataAbertura: string;
  dataAtualizacao?: string;
  comments?: Comment[];
}

export interface IncidentRequest {
  titulo: string;
  descricao?: string;
  prioridade?: 'BAIXA' | 'MEDIA' | 'ALTA' ;
  status?: 'ABERTO' | 'EM_ANDAMENTO' | 'RESOLVIDA' | 'CANCELADA';
  responsavelEmail: string;
  tags?: string[];
}

export interface Comment {
  id: string;
  incidentId: string;
  autor: string;
  mensagem: string;
  dataCriacao: string;
}

export interface CommentRequest {
  autor?: string;
  mensagem: string;
}
