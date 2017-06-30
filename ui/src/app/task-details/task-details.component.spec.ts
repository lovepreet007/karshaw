import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By }           from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { TaskDetailsComponent } from '../task-details/index';
import { Http, Response, Headers, RequestOptions, HttpModule } from '@angular/http';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import {RouterTestingModule} from '@angular/router/testing';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx'
import { AlertService, DealService } from '../services/index';
import { TaskDetailsVO } from '../vo/index';



describe('AppComponent', function () {
  let debug: DebugElement;
  let component: TaskDetailsComponent;
  let fixture: ComponentFixture<TaskDetailsComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ TaskDetailsComponent ],
            imports: [HttpModule,ReactiveFormsModule,RouterTestingModule],
            providers: [
                DealService,AlertService
            ]
        }).compileComponents();
        fixture = TestBed.createComponent(TaskDetailsComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();

    }));

   it('Should be created', () => {
        expect(component).toBeTruthy();
    });

    it('Should have component defined', () => {
        expect(component).toBeDefined() ;
    });

});
