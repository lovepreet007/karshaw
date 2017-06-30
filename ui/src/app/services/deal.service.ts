import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response } from '@angular/http';
import { Deal, Loan} from '../models/index';
import { Observable } from 'rxjs/Observable';
import { TaskDetailsVO } from '../vo/index';

@Injectable()
export class DealService {
    constructor(private http: Http) { }


    create(deal: Deal) {
        return this.http.post('/api' + '/deal/createDeal', deal);
    }
    
    createLoan(loan: Loan[]) {
        return this.http.post('/api' + '/deal/createLoan', loan);
    }
    
    getAllReviewTypes() {
        return this.http.get( '/api' + '/deal/reviewType' )
        .map( this.extractReviewType )
        .catch( this.handError )
    }
    
    getAllTaskDashboard(){
        return this.http.get( '/api' + '/deal/getTaskDashboard');        
    }
    
    getTaskDtailsById(taskDetails: TaskDetailsVO){
        return this.http.post( '/api' + '/deal/getTaskDetails', taskDetails);       
    }
    
    completeTask(taskDetails: TaskDetailsVO){
        return this.http.post('/api' + '/deal/completeTask', taskDetails);        
    }
    
    getAllPriorityTypes() {
        return this.http.get( '/api' + '/deal/priorityType' )
        .map( this.extractReviewType )
        .catch( this.handError )
    }
    
    private extractReviewType( res: Response ) {
        let body = res.json();
        return body || {}
    }

    private handError( error: any ) {
        console.error( 'post error: ', error )
        return Observable.throw( error.statusText )
    }

}