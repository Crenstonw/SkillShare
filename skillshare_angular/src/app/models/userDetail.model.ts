export interface UserDetail {
    id:             string;
    email:          string;
    username:       string;
    name:           string;
    surname:        string;
    password:       string;
    profilePicture: string;
    role:           string[];
    orders:         Order[];
    favoriteOrders: Order[];
    enabled:        boolean;
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
