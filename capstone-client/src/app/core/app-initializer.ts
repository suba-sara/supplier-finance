import { AuthService } from './auth/auth.service';

export function appInitializer(authService: AuthService): () => Promise<void> {
  return () =>
    new Promise((resolve) => {
      console.log('refresh token on app start up');
      const authToken = localStorage.getItem('access_token');
      if (authToken) {
        authService.refreshToken(authToken).subscribe().add(resolve);
      } else {
        resolve();
      }
    });
}
