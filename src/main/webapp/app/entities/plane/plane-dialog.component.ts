import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Plane } from './plane.model';
import { PlanePopupService } from './plane-popup.service';
import { PlaneService } from './plane.service';
import { Task, TaskService } from '../task';

@Component({
    selector: 'jhi-plane-dialog',
    templateUrl: './plane-dialog.component.html'
})
export class PlaneDialogComponent implements OnInit {

    plane: Plane;
    isSaving: boolean;

    tasks: Task[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private planeService: PlaneService,
        private taskService: TaskService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.taskService.query()
            .subscribe((res: HttpResponse<Task[]>) => { this.tasks = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.plane.id !== undefined) {
            this.subscribeToSaveResponse(
                this.planeService.update(this.plane));
        } else {
            this.subscribeToSaveResponse(
                this.planeService.create(this.plane));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Plane>>) {
        result.subscribe((res: HttpResponse<Plane>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Plane) {
        this.eventManager.broadcast({ name: 'planeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTaskById(index: number, item: Task) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-plane-popup',
    template: ''
})
export class PlanePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planePopupService: PlanePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.planePopupService
                    .open(PlaneDialogComponent as Component, params['id']);
            } else {
                this.planePopupService
                    .open(PlaneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
