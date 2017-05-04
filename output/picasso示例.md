picasso��Square��˾��Դ��һ��Androidͼ�λ����,��ַhttp://square.github.io/picasso/
/*************************************************
 * ����ʵ��ͼƬ���غͻ��湦�ܡ�����ֻ��Ҫһ�д��������ȫʵ��ͼƬ���첽����
 */
	Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
/*************************************************
 * Picasso����ʵ����ͼƬ�첽���صĹ��ܣ��������android�м���ͼƬʱ��Ҫ�����һЩ�������⣺
 * 1.��adapter����Ҫȡ���Ѿ�������Ұ��Χ��ImageViewͼƬ��Դ�ļ��أ�����ᵼ��ͼƬ��λ��Picasso�Ѿ������������⡣
 * 2.ʹ�ø��ӵ�ͼƬѹ��ת���������ܵļ����ڴ�����
 * 3.�Դ��ڴ��Ӳ�̶������湦��
 * ADAPTER �е����أ�Adapter�����ûᱻ�Զ���⵽��Picasso��ȡ���ϴεļ���
 */
	@Override public void getView(int position, View convertView, ViewGroup parent) {
		SquaredImageView view = (SquaredImageView) convertView;
		if (view == null) {
			view = new SquaredImageView(context);
		}
		String url = getItem(position);
		Picasso.with(context).load(url).into(view);
	}
/*************************************************
 * ͼƬת����ת��ͼƬ����Ӧ���ִ�С�������ڴ�ռ��
 */
	Picasso.with(context)
		.load(url)
		.resize(50, 50)
		.centerCrop()
		.into(imageView);

/*************************************************
 * �㻹�����Զ���ת����
 */
	public class CropSquareTransformation implements Transformation {
	@Override public Bitmap transform(Bitmap source) {
		int size = Math.min(source.getWidth(), source.getHeight());
		int x = (source.getWidth() - size) / 2;
		int y = (source.getHeight() - size) / 2;
		Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
		if (result != source) {
			source.recycle();
		}
		return result;
	}
	@Override public String key() { return "square()"; }
	}
��CropSquareTransformation �Ķ��󴫵ݸ�transform ��������
/*************************************************
 * Place holders-�հ׻��ߴ���ռλͼƬ��picasso�ṩ������ռλͼƬ��δ������ɻ��߼��ط��������ʱ��Ҫһ��ͼƬ��Ϊ��ʾ��
 */
	Picasso.with(context)
		.load(url)
		.placeholder(R.drawable.user_placeholder)
		.error(R.drawable.user_placeholder_error)
		.into(imageView);
/*************************************************/
