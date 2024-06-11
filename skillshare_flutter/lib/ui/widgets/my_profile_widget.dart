import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:skillshare_flutter/blocs/myProfile/my_profile_bloc.dart';
import 'package:skillshare_flutter/models/dtos/edit_user_request.dart';
import 'package:skillshare_flutter/models/responses/user_response.dart';
import 'package:skillshare_flutter/repositories/user/user_repository.dart';
import 'package:skillshare_flutter/repositories/user/user_repository_impl.dart';
import 'package:skillshare_flutter/ui/login_page.dart';

class MyProfileWidget extends StatefulWidget {
  const MyProfileWidget({super.key});

  @override
  State<MyProfileWidget> createState() => _MyProfileWidgetState();
}

class _MyProfileWidgetState extends State<MyProfileWidget> {
  late UserRepository userRepository;
  late MyProfileBloc _myProfileBloc;

  void _showEditModal(BuildContext context, UserResponse me) {
    final formKey = GlobalKey<FormState>();
    final TextEditingController pfController =
        TextEditingController(text: me.profilePicture);
    final TextEditingController emailController =
        TextEditingController(text: me.email);
    final TextEditingController usernameController =
        TextEditingController(text: me.username);
    final TextEditingController nameController =
        TextEditingController(text: me.name);
    final TextEditingController surnameController =
        TextEditingController(text: me.surname);

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return SingleChildScrollView(
          child: AlertDialog(
            title: const Text('Edit'),
            content: Form(
              key: formKey,
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: <Widget>[
                  TextFormField(
                    controller: pfController,
                    minLines: 2,
                    maxLines: null,
                    decoration: const InputDecoration(labelText: 'Profile picture'),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please, put a profile picture';
                      }
                      return null;
                    },
                  ),
                  TextFormField(
                    controller: emailController,
                    decoration: const InputDecoration(labelText: 'Email'),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please, put an email';
                      }
                      return null;
                    },
                  ),
                  TextFormField(
                    controller: usernameController,
                    decoration: const InputDecoration(labelText: 'Username'),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please, put an username';
                      }
                      return null;
                    },
                  ),
                  TextFormField(
                    controller: nameController,
                    decoration: const InputDecoration(labelText: 'Name'),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please, put a name';
                      }
                      return null;
                    },
                  ),
                  TextFormField(
                    controller: surnameController,
                    decoration: const InputDecoration(labelText: 'Surname'),
                    validator: (value) {
                      if (value == null || value.isEmpty) {
                        return 'Please, put a surname';
                      }
                      return null;
                    },
                  ),
                ],
              ),
            ),
            actions: <Widget>[
              TextButton(
                onPressed: () {
                  Navigator.of(context).pop();
                },
                child: const Text('Close'),
              ),
              TextButton(
                onPressed: () {
                  if (formKey.currentState!.validate()) {
                    EditUserRequest response = EditUserRequest(
                        profilePicture: pfController.text,
                        email: emailController.text,
                        username: usernameController.text,
                        name: nameController.text,
                        surname: surnameController.text);
                    userRepository.editUser(me.id, response);
                    Navigator.of(context)
                        .pop();
                  }
                },
                child: const Text('Send'),
              ),
            ],
          ),
        );
      },
    );
  }

  String date(DateTime dateTime, bool justDate) {
    List<String> date = dateTime.toString().split(' ')[0].split('-');
    List<String> time = dateTime.toString().split(' ')[1].split(':');
    time[2] = time[2].split('.')[0];
    num response;
    if (justDate) {
      return dateTime.toString().split(' ')[0];
    }
    if (date[0] != DateTime.now().year.toString()) {
      response = DateTime.now().year - int.parse(date[0]);
      return '${response} year${response != 1 ? 's' : ''} ago';
    } else if ((int.parse(date[1])) != DateTime.now().month) {
      response = DateTime.now().month - (int.parse(date[1]));
      return '${response} month${response != 1 ? 's' : ''} ago';
    } else if (int.parse(date[2]) != DateTime.now().day) {
      response = DateTime.now().day - int.parse(date[2]);
      return '${response} day${response != 1 ? 's' : ''} ago';
    } else if (int.parse(time[0]) != (DateTime.now().hour + 2)) {
      response = (DateTime.now().hour + 2) - int.parse(time[0]);
      return '${response} hour${response != 1 ? 's' : ''} ago';
    } else if (int.parse(time[1]) != DateTime.now().minute) {
      response = DateTime.now().minute - int.parse(time[1]);
      return '${response} minute${response != 1 ? 's' : ''} ago';
    } else {
      return 'Just now';
    }
  }

  _logOut() {
    Navigator.pushReplacement(context, MaterialPageRoute(builder: (context) => const LoginPage()));
  }

  @override
  void initState() {
    userRepository = UserRepositoryImpl();
    _myProfileBloc = MyProfileBloc(userRepository)..add(DoMyProfileEvent());
    super.initState();
  }

  void reload() {
    userRepository = UserRepositoryImpl();
    _myProfileBloc = MyProfileBloc(userRepository)..add(DoMyProfileEvent());
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider.value(
      value: _myProfileBloc,
      child: BlocBuilder<MyProfileBloc, MyProfileState>(
        builder: (context, state) {
          if (state is MyProfileSuccess) {
            return _buildMyProfileWidget(state.me);
          } else if (state is MyProfileError) {
            return const Text('error');
          } else if (state is MyProfileLoading) {
            return const Center(child: CircularProgressIndicator());
          }
          return const Text('data');
        },
      ),
    );
  }

  _buildMyProfileWidget(UserResponse me) {
    return Scaffold(
        backgroundColor: const Color.fromARGB(1000, 191, 218, 208),
        body: SingleChildScrollView(
          child: Column(children: [
            Padding(
                padding: const EdgeInsets.fromLTRB(0, 25, 0, 20),
                child: Container(
                    padding: const EdgeInsets.fromLTRB(20, 10, 20, 20),
                    width: MediaQuery.of(context).size.width,
                    color: Colors.white,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Row(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Container(
                              width: 200,
                              height: 200,
                              decoration: BoxDecoration(
                                shape: BoxShape.circle,
                                border: Border.all(color: Colors.black),
                              ),
                              child: ClipRRect(
                                borderRadius: BorderRadius.circular(100),
                                child: Image.network(
                                  me.profilePicture,
                                  fit: BoxFit.cover,
                                ),
                              ),
                            ),
                            Row(
                              children: [
                                const Text(
                                  'LogOut',
                                  style: TextStyle(fontWeight: FontWeight.w900),
                                ),
                                IconButton(
                                    onPressed: () {
                                      _logOut();
                                    },
                                    icon: const Icon(
                                      Icons.exit_to_app,
                                      color: Colors.black,
                                    )),
                              ],
                            )
                          ],
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Padding(
                              padding: const EdgeInsets.all(8.0),
                              child: Text(me.username,
                                  style: const TextStyle(fontSize: 30)),
                            ),
                            IconButton(
                                style: TextButton.styleFrom(
                                    backgroundColor: Colors.black),
                                onPressed: () {
                                  _showEditModal(context, me);
                                },
                                icon: const Icon(
                                  Icons.edit_square,
                                  color: Colors.white,
                                )
                            ),
                          ],
                        ),
                        Text('Created: ${date(me.createdAt, false)}')
                      ],
                    ))),
          Padding(
                padding: const EdgeInsets.fromLTRB(0, 25, 0, 20),
                child: Container(
                    padding: const EdgeInsets.fromLTRB(20, 10, 20, 20),
                    width: MediaQuery.of(context).size.width,
                    color: Colors.white,
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              const Text('Email:'),
                              Text(me.email),
                            ],
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              const Text('Name:'),
                              Text(me.name),
                            ],
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.all(8.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              const Text('Surname:'),
                              Text(me.surname),
                            ],
                          ),
                        ),
                      ],
                    )
                    ))]),
        ));
  }
}
