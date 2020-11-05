import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Book } from '@app/models/book.model';
import { Author } from '@app/models/author.model';
import { Category } from '@app/models/category.model';


const routes = {
  api: (url: String) => `${url}/api/v1`,
  book: (url: String) => `${url}/api/v1/book`,
  author: (url: String) => `${url}/api/v1/author`,
  category: (url: String) => `${url}/api/v1/category`,
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
export class BookRegisterService {
  constructor(private httpClient: HttpClient) {}

  url = 'http://localhost:8080';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  registerAuthor(author: Author): Observable<AjaxResponse<Author[]>> {
    return this.httpClient.post<AjaxResponse<Author[]>>(routes.author(this.url),
      author
    );
  }

  registerCategory(category: Category): Observable<AjaxResponse<Category[]>> {
    return this.httpClient.post<AjaxResponse<Category[]>>(routes.category(this.url),
      category
    );
  }

  registerBook(book: Book): Observable<AjaxResponse<Book[]>> {
    return this.httpClient.post<AjaxResponse<Book[]>>(routes.book(this.url),
      book
    );
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
