import { waitForAsync, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FlexLayoutModule } from '@angular/flex-layout';

import { CoreModule } from '@core';
import { SharedModule } from '@shared';
import { MaterialModule } from '@app/material.module';
import { BookRegisterComponent } from './book-register.component';
import { BookRegisterService } from './book-register.service';

describe('BookRegisterComponent', () => {
  let component: BookRegisterComponent;
  let fixture: ComponentFixture<BookRegisterComponent>;

  beforeEach(
    waitForAsync(() => {
      TestBed.configureTestingModule({
        imports: [
          BrowserAnimationsModule,
          FlexLayoutModule,
          MaterialModule,
          CoreModule,
          SharedModule,
          HttpClientTestingModule,
        ],
        declarations: [BookRegisterComponent],
        providers: [BookRegisterService],
      }).compileComponents();
    })
  );

  beforeEach(() => {
    fixture = TestBed.createComponent(BookRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
