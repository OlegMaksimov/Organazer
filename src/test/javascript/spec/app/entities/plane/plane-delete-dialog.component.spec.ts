/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { OrganazerTestModule } from '../../../test.module';
import { PlaneDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/plane/plane-delete-dialog.component';
import { PlaneService } from '../../../../../../main/webapp/app/entities/plane/plane.service';

describe('Component Tests', () => {

    describe('Plane Management Delete Component', () => {
        let comp: PlaneDeleteDialogComponent;
        let fixture: ComponentFixture<PlaneDeleteDialogComponent>;
        let service: PlaneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OrganazerTestModule],
                declarations: [PlaneDeleteDialogComponent],
                providers: [
                    PlaneService
                ]
            })
            .overrideTemplate(PlaneDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlaneDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlaneService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
