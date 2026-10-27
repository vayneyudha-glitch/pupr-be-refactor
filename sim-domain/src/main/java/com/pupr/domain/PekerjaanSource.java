package com.pupr.domain;

import java.util.List;

public interface PekerjaanSource {

    List<Pekerjaan> findByBidangCode(String bidangCode);
}
