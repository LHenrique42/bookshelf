import { Type } from '@angular/core';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { CoreModule } from '@core';
import { BookRegisterService } from './book-register.service';

describe('BookRegisterService', () => {
  let bookRegisterService: BookRegisterService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CoreModule, HttpClientTestingModule],
      providers: [BookRegisterService],
    });

    bookRegisterService = TestBed.inject(BookRegisterService);
    httpMock = TestBed.inject(HttpTestingController as Type<HttpTestingController>);
  });

  afterEach(() => {
    httpMock.verify();
  });

  describe('', () => {
    //
  });
});
