/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { OrganazerTestModule } from '../../../test.module';
import { PlaneDetailComponent } from '../../../../../../main/webapp/app/entities/plane/plane-detail.component';
import { PlaneService } from '../../../../../../main/webapp/app/entities/plane/plane.service';
import { Plane } from '../../../../../../main/webapp/app/entities/plane/plane.model';

describe('Component Tests', () => {

    describe('Plane Management Detail Component', () => {
        let comp: PlaneDetailComponent;
        let fixture: ComponentFixture<PlaneDetailComponent>;
        let service: PlaneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [OrganazerTestModule],
                declarations: [PlaneDetailComponent],
                providers: [
                    PlaneService
                ]
            })
            .overrideTemplate(PlaneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PlaneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlaneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Plane(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.plane).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
