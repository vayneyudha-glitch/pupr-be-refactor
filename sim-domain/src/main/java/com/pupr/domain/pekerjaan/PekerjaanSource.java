package com.pupr.domain.pekerjaan;

import java.util.List;

public interface PekerjaanSource {

    List<Pekerjaan> findByBidangCode(String bidangCode);
}
