import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { Plane } from './plane.model';
import { PlaneService } from './plane.service';

@Injectable()
export class PlanePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private planeService: PlaneService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.planeService.find(id)
                    .subscribe((planeResponse: HttpResponse<Plane>) => {
                        const plane: Plane = planeResponse.body;
                        plane.dateStart = this.datePipe
                            .transform(plane.dateStart, 'yyyy-MM-ddTHH:mm:ss');
                        plane.dateEnd = this.datePipe
                            .transform(plane.dateEnd, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.planeModalRef(component, plane);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.planeModalRef(component, new Plane());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    planeModalRef(component: Component, plane: Plane): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.plane = plane;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
