import { Type } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { CoreModule } from '@core';
import { BookListService } from './book-list.service';

describe('BookRegisterService', () => {
  let bookListService: BookListService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CoreModule, HttpClientTestingModule],
      providers: [BookListService],
    });

    bookListService = TestBed.inject(BookListService);
    httpMock = TestBed.inject(HttpTestingController as Type<HttpTestingController>);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('', () => {
    //
  });
});
