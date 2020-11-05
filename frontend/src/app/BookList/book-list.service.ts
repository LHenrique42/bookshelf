import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { map, catchError, retry } from 'rxjs/operators';

import { Book } from '../models/book.model';

const routes = {
  book: (url: String) => `${url}/api/v1/book`,
};

interface AjaxResponse<T> extends Iterator<T>{
  success : boolean,
  errorMessage? : string,
  data : T,
  [Symbol.iterator](): IterableIterator<T>;
}

@Injectable({
  providedIn: 'root',
})
export class BookListService {
  constructor(private httpClient: HttpClient) {}

  url = 'http://localhost:8080';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  getBookList(): Observable<AjaxResponse<Book[]>> {
    return this.httpClient.get<AjaxResponse<Book[]>>(routes.book(this.url));
  }

  handleError(error: HttpErrorResponse) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Erro ocorreu no lado do client
      errorMessage = error.error.message;
    } else {
      // Erro ocorreu no lado do servidor
      errorMessage = `Código do erro: ${error.status}, ` + `menssagem: ${error.message}`;
    }
    console.log(errorMessage);
    return throwError(errorMessage);
  };
}
