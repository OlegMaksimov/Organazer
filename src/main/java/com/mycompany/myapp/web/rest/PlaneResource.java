package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Plane;

import com.mycompany.myapp.repository.PlaneRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Plane.
 */
@RestController
@RequestMapping("/api")
public class PlaneResource {

    private final Logger log = LoggerFactory.getLogger(PlaneResource.class);

    private static final String ENTITY_NAME = "plane";

    private final PlaneRepository planeRepository;

    public PlaneResource(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    /**
     * POST  /planes : Create a new plane.
     *
     * @param plane the plane to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plane, or with status 400 (Bad Request) if the plane has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/planes")
    @Timed
    public ResponseEntity<Plane> createPlane(@RequestBody Plane plane) throws URISyntaxException {
        log.debug("REST request to save Plane : {}", plane);
        if (plane.getId() != null) {
            throw new BadRequestAlertException("A new plane cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plane result = planeRepository.save(plane);
        return ResponseEntity.created(new URI("/api/planes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /planes : Updates an existing plane.
     *
     * @param plane the plane to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plane,
     * or with status 400 (Bad Request) if the plane is not valid,
     * or with status 500 (Internal Server Error) if the plane couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/planes")
    @Timed
    public ResponseEntity<Plane> updatePlane(@RequestBody Plane plane) throws URISyntaxException {
        log.debug("REST request to update Plane : {}", plane);
        if (plane.getId() == null) {
            return createPlane(plane);
        }
        Plane result = planeRepository.save(plane);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plane.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planes : get all the planes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of planes in body
     */
    @GetMapping("/planes")
    @Timed
    public List<Plane> getAllPlanes() {
        log.debug("REST request to get all Planes");
        return planeRepository.findAll();
        }

    /**
     * GET  /planes/:id : get the "id" plane.
     *
     * @param id the id of the plane to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plane, or with status 404 (Not Found)
     */
    @GetMapping("/planes/{id}")
    @Timed
    public ResponseEntity<Plane> getPlane(@PathVariable Long id) {
        log.debug("REST request to get Plane : {}", id);
        Plane plane = planeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plane));
    }

    /**
     * DELETE  /planes/:id : delete the "id" plane.
     *
     * @param id the id of the plane to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/planes/{id}")
    @Timed
    public ResponseEntity<Void> deletePlane(@PathVariable Long id) {
        log.debug("REST request to delete Plane : {}", id);
        planeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
