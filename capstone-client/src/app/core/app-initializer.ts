import { AuthService } from './auth/auth.service';

export function appInitializer(authService: AuthService): () => Promise<void> {
  return () =>
    new Promise((resolve) => {
      const authToken = localStorage.getItem('access_token');
      if (authToken) {
        authService.refreshToken().subscribe().add(resolve);
      } else {
        resolve();
      }
    });
}
