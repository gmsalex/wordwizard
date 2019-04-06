export interface UserAuthRequest {
  readonly email: string;
  readonly password: string;
}

export interface UserAuthSuccessResponse {
  readonly name: string;
}

