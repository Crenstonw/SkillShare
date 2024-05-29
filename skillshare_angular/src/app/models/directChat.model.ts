export interface DirectMessage {
    title:       string;
    message:     string;
    dateTime:    Date;
    isMyMessage: boolean;
    userFrom:    User;
    userTo:      User;
}

export interface User {
    id:        string;
    email:     string;
    username:  string;
    createdAt: string;
    admin:     boolean;
}

