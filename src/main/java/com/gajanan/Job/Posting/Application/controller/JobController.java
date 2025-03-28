package com.gajanan.Job.Posting.Application.controller;


import com.gajanan.Job.Posting.Application.config.aspect.NoLogging;
import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.dto.ResponseDTO;
import com.gajanan.Job.Posting.Application.model.entity.Job;
import com.gajanan.Job.Posting.Application.service.CacheInspectionService;
import com.gajanan.Job.Posting.Application.service.JobService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Job Controller", description = "Endpoints for managing Jobs")
@Slf4j
@Validated
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final CacheInspectionService cacheInspectionService;

    @Operation(
            description = "POST endpoint for Job"
    )
    @PostMapping
    public ResponseEntity<ResponseDTO> createJob(@Valid @RequestBody RequestDTO requestDTO) {

        Job createdJob = jobService.createJob(requestDTO);
        return new ResponseEntity<>(new ResponseDTO(
                "Success",
                "Job Created Successfully",
                createdJob
        ), HttpStatus.CREATED);
    }

    @Operation(
            description = "GET endpoint for all Jobs"
    )
    @GetMapping
    @NoLogging
    public ResponseEntity<ResponseDTO> getAllJobs() {

        List<Job> allJobs = jobService.getAllJobs();
        if(allJobs.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(
                    "No Jobs Available",
                    "Currently, there are no job postings available. Please check back later."
            ),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ResponseDTO(
                    "Success",
                    "Jobs Retrieved Successfully",
                    allJobs
            ), HttpStatus.OK);
        }
    }

    @Operation(
            description = "GET endpoint for Job based on it's ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getJob(@PathVariable Long id) {
        Job job = jobService.getJob(id);
        return new ResponseEntity<>(new ResponseDTO(
                "Success",
                "Job Retrieved Successfully",
                job
        ), HttpStatus.OK);
    }

    @Operation(
            description = "PUT endpoint for Job"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateJob(@PathVariable Long id, @Valid @RequestBody RequestDTO requestDTO) {
        Job updatedJob = jobService.updateJob(id, requestDTO);
        return new ResponseEntity<>(new ResponseDTO(
                "Success",
                "Job Updated Successfully",
                updatedJob
        ), HttpStatus.OK);
    }

    @Operation(
            description = "DELETE endpoint for Job"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteJob(@PathVariable Long id) {
        Job deletedJob = jobService.deleteJob(id);
        return new ResponseEntity<>(new ResponseDTO(
                "Success",
                "Job Deleted Successfully",
                deletedJob
        ), HttpStatus.OK);
    }

    @GetMapping("/cacheData")
    public void getCacheData(){
        cacheInspectionService.printCacheContent("job");
    }

}
