export interface OrderDetail {
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
    messages:         Message[];
}

export interface Message {
    id:          string;
    title:       string;
    message:     string;
    isMyMessage: boolean;
    dateTime:    Date | null;
    author:      Author;
}

export interface Author {
    id:        string;
    email:     string;
    username:  string;
    createdAt: string;
    admin:     boolean;
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
