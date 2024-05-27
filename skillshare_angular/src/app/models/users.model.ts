export interface Users {
    content:          User[];
    pageable:         Pageable;
    totalPages:       number;
    totalElements:    number;
    last:             boolean;
    number:           number;
    size:             number;
    numberOfElements: number;
    sort:             Sort;
    first:            boolean;
    empty:            boolean;
}

export interface User {
    id:             string;
    email:          string;
    username:       string;
    name:           string;
    surname:        string;
    password:       string;
    profilePicture: string;
    userRole:       string;
    createdAt:      Date;
    enabled:        boolean;
}

export interface Pageable {
    pageNumber: number;
    pageSize:   number;
    sort:       Sort;
    offset:     number;
    paged:      boolean;
    unpaged:    boolean;
}

export interface Sort {
    sorted:   boolean;
    empty:    boolean;
    unsorted: boolean;
}
