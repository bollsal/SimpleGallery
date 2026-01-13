package com.bollsal.simplegallery.domain.interfaces

interface Mapper<From, TO> {
  fun map(from: From): TO
}
