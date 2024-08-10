MyHelper
=====

An Android library that includes base implementations of commonly used classes across many projects.

This library will streamline the setup process for new projects, significantly reducing the time required to get started.

Implementations that I will get
-------------------------------

1. [BaseActivity][1]
2. [BaseFragment][2]
3. [BaseDialog][3]
4. [BaseInternetActivity][4]
5. [BaseViewModel][5]
6. [BaseAdapter][6]
7. [BaseItemSelectableAdapter][7]
8. [Notification Utils][8]
9. [Extensions][9]
10. [BaseInterfaces][10]
11. [CommonParser][11]

and more, check the [library classes][12] to know in depth.

Download
--------

You can download a jar from GitHub's [releases page][13].

Or use Gradle:

```gradle
repositories {
  google()
  mavenCentral()
  maven { url 'https://jitpack.io' }
}

dependencies {
	 implementation 'com.github.amanrj33:MyHelper:1.0.4'
}
```

Some must-have dependencies to include in your project for successful sync
--------------------------------------------------------------------------

1. Gson: For API base implementation
2. Easy Permissions: For permission base implementation
3. Swipe Refresh Layout: For refresh handling
   
```gradle
dependencies {
    implementation 'com.google.code.gson:gson:2.10.1'
    implementation 'pub.devrel:easypermissions:3.0.0'
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
}
```

How do I use MyHelper?
----------------------

Simple use cases will look something like this:

1. Activity
   
  ```kotlin
  class ExampleActivity : BaseActivity<ActivityExampleBinding>() {
      override fun getMyBinding(): ActivityExampleBinding {
          return ActivityExampleBinding.inflate(layoutInflater)
      }
  }
  ```

2. Fragment
   
  ```kotlin
  class ExampleFragment : BaseFragment<FragmentExampleBinding>() {
      override fun getMyBinding(): FragmentExampleBinding {
          return FragmentExampleBinding.inflate(layoutInflater)
      }
  }
  ```

3. Adapter

  ```kotlin
  class ExampleAdapter(
      context: Context,
      data: ArrayList<ExampleModel>, 
      listener: OnListItemClickListener<ExampleModel>?
  ) : BaseAdapter<ExampleModel, RowItemBinding>(context, data, listener) {
      override fun createBinding(
          viewType: Int,
          inflater: LayoutInflater,
          parent: ViewGroup
      ): RowItemBinding {
          return RowItemBinding.inflate(inflater, parent, false)
      }

      override fun prepareView(
          holder: BaseViewHolder<RowItemBinding>,
          position: Int,
          model: ExampleModel
      ) {
          holder.apply { 
              //bind the views of the holder for data population
          }
      }
  }
  ```

Contributions
-------------

Want something more?

Create a feature request or send me a PR.

Happy Coding!!!

[1]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/view/controller/BaseActivity.kt
[2]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/view/controller/BaseFragment.kt
[3]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/view/controller/BaseDialog.kt
[4]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/view/network/BaseInternetActivity.kt
[5]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/view/BaseViewModel.kt
[6]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/view/adapters/recycler/BaseAdapter.kt
[7]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/view/adapters/recycler/BaseItemSelectableAdapter.kt
[8]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/utils/Notifications.kt
[9]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/support/extensions
[10]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/interfaces
[11]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper/data/parsers/CommonParser.kt
[12]: https://github.com/amanrj33/MyHelper/blob/release/my-helper/src/main/java/com/aman/androidlibrary/my_helper
[13]: https://github.com/amanrj33/MyHelper/releases
