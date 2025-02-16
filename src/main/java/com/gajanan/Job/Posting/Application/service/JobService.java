package com.gajanan.Job.Posting.Application.service;


import com.gajanan.Job.Posting.Application.model.dto.RequestDTO;
import com.gajanan.Job.Posting.Application.model.entity.Job;

import java.util.List;

public interface JobService {
    Job createJob(RequestDTO requestDTO);

    List<Job> getAllJobs();

    Job getJob(Long id);

    Job updateJob(Long id, RequestDTO requestDTO);

    Job deleteJob(Long id);
}
