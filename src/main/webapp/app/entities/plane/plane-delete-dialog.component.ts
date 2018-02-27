import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Plane } from './plane.model';
import { PlanePopupService } from './plane-popup.service';
import { PlaneService } from './plane.service';

@Component({
    selector: 'jhi-plane-delete-dialog',
    templateUrl: './plane-delete-dialog.component.html'
})
export class PlaneDeleteDialogComponent {

    plane: Plane;

    constructor(
        private planeService: PlaneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.planeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'planeListModification',
                content: 'Deleted an plane'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-plane-delete-popup',
    template: ''
})
export class PlaneDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private planePopupService: PlanePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.planePopupService
                .open(PlaneDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
