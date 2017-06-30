import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx'
import { AlertService, DealService } from '../services/index';
import { TaskDetailsVO } from '../vo/index';

@Component( {
    moduleId: module.id,
    styleUrls: ['./task.component.css'],
    templateUrl: 'task.component.html'
} )
export class TaskComponent {
    pageTitle: string = 'Task Dashboard';
    public taskDetails: TaskDetailsVO[];

    constructor(
        private http: Http,
        private router: Router,
        private route: ActivatedRoute,
        private dealService: DealService,
        private alertService: AlertService) {
        
        this.getAllTaskDashboard()
            .map(res => res.json())
            .subscribe(
                    data => { 
                        this.taskDetails = data
                        //console.log(JSON.stringify(this.taskDetails))
                    },
                    err => console.log( 'get error: ', err )
            )
    }
    
    getAllTaskDashboard(): Observable<any> {
        return this.dealService.getAllTaskDashboard();
    }

    clicked( event ) {
        this.router.navigate( ['deal'] );
        event.preventDefault();
    }

    taskClick(event ) {
        this.router.navigate(['taskDetails',event.loanId,event.taskName]);
    }

}