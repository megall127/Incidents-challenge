export interface LoginRequest {
  email: string;
  senha: string;
}

export interface LoginResponse {
  token: string;
  message: string;
}

export interface RegisterRequest {
  nome: string;
  email: string;
  senha: string;
}

export interface RegisterResponse {
  message: string;
}
