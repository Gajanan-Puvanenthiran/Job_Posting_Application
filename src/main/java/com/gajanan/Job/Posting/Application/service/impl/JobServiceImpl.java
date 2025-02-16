package com.gajanan.Job.Posting.Application.service.impl;

import com.gajanan.Job.Posting.Application.exception.JobNotFoundException;

import com.gajanan.Job.Posting.Application.mapper.RequestDTOConverter;
import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.entity.Job;
import com.gajanan.Job.Posting.Application.repository.JobRepository;
import com.gajanan.Job.Posting.Application.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    public Job createJob(RequestDTO requestDTO) {
        Job job = RequestDTOConverter.convertDTOtoJob(requestDTO);
        return jobRepository.save(job);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job getJob(Long id) {
        return jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job with id " + id + " not found"));
    }

    @Override
    public Job updateJob(Long id, RequestDTO requestDTO) {
        Job toUpdatableJob = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job with id " + id + " not found"));
        RequestDTOConverter.convertDTOtoJob(requestDTO, toUpdatableJob);
        return jobRepository.save(toUpdatableJob);
    }

    @Override
    public Job deleteJob(Long id) {
        Job toDeletableJob = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException("Job with id " + id + " not found"));
        jobRepository.delete(toDeletableJob);
        return toDeletableJob;
    }
}
