package nyang.puzzlebackend.api.common;

import java.util.List;

public record PageContent<R>(
  List<R> items,
  int currentPage,
  int totalPages,
  long totalItemCount,
  int pageSize
) { }
