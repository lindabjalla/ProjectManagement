package se.grouprich.projectmanagement.repository;

public class UserRepositoryImpl implements UserRepositoryCustom
{
	@Override
	public boolean isLengthInRange(String username)
	{
		if(username != null && !username.trim().isEmpty())
		{
			if(username.length() >= 10)
			{
				return true;
			}
		}
		return false;
	}
}
