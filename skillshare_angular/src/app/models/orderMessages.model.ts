export interface Messages {
    content:          Message[];
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

export interface Message {
    id:          string;
    title:       string;
    message:     string;
    isMyMessage: boolean;
    dateTime:    Date;
    author:      Author;
    order:       Order;
}

export interface Author {
    id:        string;
    email:     string;
    username:  string;
    createdAt: string;
    admin:     boolean;
}

export interface Order {
    id:               string;
    title:            string;
    description:      string;
    price:            number;
    state:            string;
    createdAt:        Date;
    lastTimeModified: Date;
    isAboutToExpire:  boolean;
    user:             User;
    tags:             Tag[];
}

export interface Tag {
    id:   string;
    name: string;
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

