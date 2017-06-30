import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/Rx'
import { AlertService, DealService } from '../services/index';
import { TaskDetailsVO } from '../vo/index';


@Component({
  templateUrl: './task-details.component.html',
  styleUrls: ['./task-details.component.css']
})
export class TaskDetailsComponent implements OnInit {
   public taskDetails: TaskDetailsVO[];
    loanId;
    taskName;
    taskDetailsVo;TaskDetailsVO={};
    taskDetaillsVo;
    //TODO
    ui_taskId;ui_taskNme;
    ui_description;
    ui_creationTime;
   constructor(
           private http: Http,
           private router: Router,
           private route: ActivatedRoute,
           private dealService: DealService,
           private alertService: AlertService,
           private _routeParams:ActivatedRoute,) {

             this._routeParams.params.subscribe(params => {
   this.loanId = params['loanId'];
   this.taskName = params['taskName'];
    });
    


   }
   ngOnInit() {
    this.taskDetaillsVo=new TaskDetailsVO(null,null,null,this.loanId,this.taskName,null,null,null);
        this.getTaskDtailsById()
            .map(res => res.json())
            .subscribe(
                    data => { 
                        this.taskDetails = data;
                        this.ui_taskId=this.taskDetails[0].taskId;
                        this.ui_taskNme=this.taskDetails[0].taskName;
                        this.ui_description=this.taskDetails[0].description;
                        this.ui_creationTime=this.taskDetails[0].timesStamp;
                        //console.log(JSON.stringify(this.taskDetails))
                    },
                    err => console.log( 'get error: ', err )
            )
  
  }
  getTaskDtailsById(): Observable<any> {
        return this.dealService.getTaskDtailsById(this.taskDetaillsVo);
    }

   cancel(event) {
    this.router.navigate(['taskDashboard']);
    event.preventDefault();
    }

    done(event) {
         this.getTaskDtailsCompleted()
            .map(res => res.json())
            .subscribe(
                    data => { 
                        this.router.navigate(['taskDashboard']);
                        event.preventDefault();
                    },
                    err => console.log( 'get error: ', err )
            )    
}

    getTaskDtailsCompleted(): Observable<any> {
        this.taskDetaillsVo=new TaskDetailsVO(this.ui_taskId,null,null,null,null,null,null,null);
        return this.dealService.completeTask(this.taskDetaillsVo);
    }
  
  

}
