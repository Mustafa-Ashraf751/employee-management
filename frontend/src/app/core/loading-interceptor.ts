import { HttpInterceptorFn } from '@angular/common/http';
import { finalize } from 'rxjs/internal/operators/finalize';
import { inject } from '@angular/core';
import { LoadingService } from '../services/loader.service';

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const loading = inject(LoadingService);

  loading.show();

  return next(req).pipe(
    finalize(() => loading.hide())
  );
};
